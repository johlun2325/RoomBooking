package com.example.roombooking.utilities;

import java.time.LocalDate;

public interface Utility {

    LocalDate convertToLocalDate(String date);

    boolean areDatesOverlapping(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2);

}
