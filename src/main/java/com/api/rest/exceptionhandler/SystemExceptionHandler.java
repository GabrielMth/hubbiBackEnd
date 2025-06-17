package com.api.rest.exceptionhandler;

import com.api.rest.service.exceptionDeRegraDeNegocio.SenhaAtualIncorretaException;
import com.api.rest.service.exceptionDeRegraDeNegocio.UsuarioTaskMovimentoException;
import com.api.rest.service.exceptionDeRegraDeNegocio.PessoaInexistenteOuInativaException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
public class SystemExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override   //atributo não existente
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String mensagemUsuario = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        return handleExceptionInternal(ex ,erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ AccessDeniedException.class})
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        String mensagemUsuario = messageSource.getMessage("sem.permissao", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        return new ResponseEntity<>(erros, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ AuthenticationException.class, HttpClientErrorException.Unauthorized.class })
    public ResponseEntity<Object> handleUnauthorized(Exception ex, WebRequest request) {
        String mensagemUsuario = messageSource.getMessage("credenciais.incorretas", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        return new ResponseEntity<>(erros, new HttpHeaders(), HttpStatus.UNAUTHORIZED); // 401 Unauthorized
    }

    @ExceptionHandler({ EmptyResultDataAccessException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {

        String mensagemUsuario = messageSource.getMessage("not.found", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = (ex.getCause() != null) ? ex.getCause().toString() : ex.toString();

        List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException (DataIntegrityViolationException ex, WebRequest request) {

        String mensagemUsuario = messageSource.getMessage("operacao.nao.permitida", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);

        List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(PessoaInexistenteOuInativaException.class)
    public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
        String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(UsuarioTaskMovimentoException.class)
    public ResponseEntity<Object> handleNegocioException(UsuarioTaskMovimentoException ex, WebRequest request) {
        String mensagemUsuario = messageSource.getMessage("usuario.movimento-contem", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<Erro> erros = List.of(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(SenhaAtualIncorretaException.class)
    public ResponseEntity<List<Map<String, String>>> handleSenhaAtualIncorreta(SenhaAtualIncorretaException ex) {
        Map<String, String> body = Map.of(
                "mensagemUsuario", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(List.of(body));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<Erro> erros = criarListaDeErros(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<Erro> criarListaDeErros (BindingResult bindingResult) {
        List<Erro> erros= new ArrayList<>();

        for (FieldError error : bindingResult.getFieldErrors() ) {
            String mensagemUsuario = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            String mensagemDesenvolvedor = error.toString();

            erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        }
        return erros;
    }

    public static class Erro {

        public String mensagemUsuario;
        public String mensagemDesenvolvedor;

        public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
            this.mensagemUsuario = mensagemUsuario;
            this.mensagemDesenvolvedor = mensagemDesenvolvedor;
        }

        public String getMensagemUsuario() {
            return mensagemUsuario;
        }

        public String getMensagemDesenvolvedor() {
            return mensagemDesenvolvedor;
        }
    }
}