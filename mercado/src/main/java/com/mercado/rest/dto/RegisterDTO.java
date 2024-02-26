package com.mercado.rest.dto;

import com.mercado.entity.RegraUsuario;

public record RegisterDTO(String login, String password, RegraUsuario role) {

}
