package com.barun.tickets.services;

import com.barun.tickets.domain.enitities.QrCode;
import com.barun.tickets.domain.enitities.Ticket;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);
}
