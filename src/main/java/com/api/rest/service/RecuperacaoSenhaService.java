package com.api.rest.service;

import com.api.rest.model.Usuario;
import com.api.rest.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RecuperacaoSenhaService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    private final Map<String, String> senhasTemporarias = new ConcurrentHashMap<>();

    public RecuperacaoSenhaService(UserRepository userRepository, JavaMailSender mailSender, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public void enviarSenhaTemporaria(String email) throws MessagingException {
        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String senhaTemp = gerarSenhaAleatoria(8);
        senhasTemporarias.put(email, senhaTemp);

        // Criar conteúdo HTML
        String htmlContent = "<html>" +
                "<head>" +
                "<style>" +
                "  body { font-family: Arial, sans-serif; color: #333; background-color: #f4f4f9; padding: 20px; }" +
                "  .container { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); }" +
                "  .header { background-color: #2b2b3c; color: #fff; padding: 10px 0; text-align: center; border-radius: 8px 8px 0 0; }" +
                "  .footer { text-align: center; margin-top: 20px; font-size: 12px; color: #777; }" +
                "  .button { background-color: #4CAF50; color: white; padding: 12px 20px; border: none; cursor: pointer; text-align: center; text-decoration: none; border-radius: 5px; }" +
                "  .button:hover { background-color: #45a049; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<div class='header'>" +
                "<h2>Hubbi Digital | Recuperação de Senha</h2>" +
                "</div>" +
                "<p>Olá, " + usuario.getNome() + "!</p>" +
                "<p>Você solicitou a recuperação de senha. Sua senha temporária é:</p>" +
                "<p style='font-size: 18px; font-weight: bold; color: #FF0000;'>" + senhaTemp + "</p>" +
                "<p>Use essa senha para confirmar a troca. Caso não tenha solicitado a recuperação, ignore este e-mail.</p>" +
                "</div>" +
                "<div class='footer'>" +
                "<p>Se você não conseguiu acessar a plataforma, por favor, entre em contato com o suporte.</p>" +
                "</div>" +
                "</body>" +
                "</html>";


        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
        message.setFrom("no-reply@seuapp.com");
        message.setTo(email);
        message.setSubject("Sua senha temporária");
        message.setText(htmlContent, true);

        mailSender.send(mimeMessage);
    }

    public void confirmarTrocaSenha(String email, String senhaTemporaria, String novaSenha) {
        String senhaSalva = senhasTemporarias.get(email);

        if (senhaSalva == null || !senhaSalva.equals(senhaTemporaria)) {
            throw new RuntimeException("Senha temporária inválida ou expirou.");
        }

        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        usuario.setPassword(passwordEncoder.encode(novaSenha));
        userRepository.save(usuario);

        senhasTemporarias.remove(email);
    }

    private String gerarSenhaAleatoria(int tamanho) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(tamanho);
        for (int i = 0; i < tamanho; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
