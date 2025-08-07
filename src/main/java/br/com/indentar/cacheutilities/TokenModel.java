/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.indentar.cacheutilities;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author indentar
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenModel {

    private String access_token;
    private String token_type;
    private Long expires_in;
    private Long refresh_expires_in;
    private String scope;
    private LocalDateTime dtExp;

    public void expiresToLocalDate() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime plusNanos = localDateTime.plusSeconds(expires_in - 10);//-10 segundos para garantir
        setDtExp(plusNanos);
    }
}
