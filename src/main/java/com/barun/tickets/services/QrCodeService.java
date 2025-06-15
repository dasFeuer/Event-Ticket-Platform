package com.barun.tickets.services;

import com.barun.tickets.domain.enitities.QrCode;
import com.barun.tickets.domain.enitities.Ticket;

import java.util.UUID;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticket);

}
