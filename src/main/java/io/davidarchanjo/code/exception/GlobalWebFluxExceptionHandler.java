package io.davidarchanjo.code.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.davidarchanjo.code.model.dto.ErrorDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class GlobalWebFluxExceptionHandler implements ErrorWebExceptionHandler {

  private final ObjectMapper objectMapper;
  private final GlobalWebMVCExceptionHandler globalExceptionHandler;

  @SneakyThrows
  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable th) {
    final Mono<ErrorDTO> error = switch (th) {
      case AppNotFoundException ignored -> this.globalExceptionHandler.handlerAppNotFoundException((AppNotFoundException) th);
      case AppAlreadyExistException ignored -> this.globalExceptionHandler.handlerAppAlreadyExistException((AppAlreadyExistException) th);
      case WebExchangeBindException ignored -> this.globalExceptionHandler.handlerWebExchangeBindException((WebExchangeBindException) th);
      default -> this.globalExceptionHandler.handlerException((Exception) th);
    };

    return error.flatMap(o -> {
      DataBuffer dataBuffer;
      DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
      try {
        dataBuffer = bufferFactory.wrap(objectMapper.writeValueAsBytes(o));
      } catch (JsonProcessingException e) {
        dataBuffer = bufferFactory.wrap("".getBytes());
      }
      exchange.getResponse().setStatusCode(o.getHttpStatus());
      exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
      return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    });
  }

}