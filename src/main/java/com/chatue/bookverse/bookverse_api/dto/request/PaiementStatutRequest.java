package com.chatue.bookverse.bookverse_api.dto.request;

import com.chatue.bookverse.bookverse_api.entity.StatutPaiement;

import lombok.Builder;

@Builder
public record PaiementStatutRequest( StatutPaiement statutPaiement) {

}
