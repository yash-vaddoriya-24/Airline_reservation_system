package org.example.ar.Repository;

import org.example.ar.Models.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail, String> {

    BookingDetail findBookingDetailByPNR(String PNR);

    BookingDetail save(BookingDetail bookingDetail);

    Integer removeBookingDetailByPNR(String PNR);


}
