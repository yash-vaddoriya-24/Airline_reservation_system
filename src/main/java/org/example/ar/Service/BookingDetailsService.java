package org.example.ar.Service;

import org.example.ar.Models.BookingDetail;
import org.example.ar.Repository.BookingDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookingDetailsService {

    @Autowired
    BookingDetailRepository bookingDetailRepository;

    public Integer deleteBookingDetailByPNR(String PNR){
        try {
            return bookingDetailRepository.removeBookingDetailByPNR(PNR);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public BookingDetail saveBookingDetail(BookingDetail bookingDetail) {
        try {
            return bookingDetailRepository.save(bookingDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public BookingDetail getBookingDetailByPNR(String PNR) {
        try {
            return bookingDetailRepository.findBookingDetailByPNR(PNR);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
