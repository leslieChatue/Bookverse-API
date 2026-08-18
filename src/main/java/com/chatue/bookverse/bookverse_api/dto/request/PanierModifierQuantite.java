package com.chatue.bookverse.bookverse_api.dto.request;

import java.util.Objects;

public record PanierModifierQuantite(Long id,Long livreId,int quantite) {

	public PanierModifierQuantite{
		Objects.requireNonNull(id, "l'id du panier ne doit pas être  null");
		Objects.requireNonNull(livreId, "l'id du livre ne doit pas être  null");
		Objects.requireNonNull(quantite, "l'id du livre ne doit pas être  null");

	}

	
}
