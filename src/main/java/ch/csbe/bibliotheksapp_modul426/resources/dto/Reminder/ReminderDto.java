package ch.csbe.bibliotheksapp_modul426.resources.dto.Reminder;

import lombok.Data;

@Data
public class ReminderDto {
    private int id;
    private boolean emailSent;
    private int loanId;
}