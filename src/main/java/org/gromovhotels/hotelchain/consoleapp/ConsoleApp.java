package org.gromovhotels.hotelchain.consoleapp;

import asg.cliche.Command;
import asg.cliche.Param;
import net.datafaker.Faker;
import org.gromovhotels.hotelchain.booking.BookingService;
import org.gromovhotels.hotelchain.file.ApplicationStateRepository;
import org.gromovhotels.hotelchain.file.FileDataManipulationService;
import org.gromovhotels.hotelchain.guest.HotelGuest;
import org.gromovhotels.hotelchain.guest.HotelGuestService;
import org.gromovhotels.hotelchain.hotel.HotelService;
import org.gromovhotels.hotelchain.room.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.gromovhotels.hotelchain.utils.GeneralUtils.times;
import static org.gromovhotels.hotelchain.utils.JsonUtils.toJsonString;

@Service
public final class ConsoleApp  {

    @Autowired private Faker faker;

    @Autowired private HotelService hotelService;
    @Autowired private HotelRoomService hotelRoomService;
    @Autowired private HotelGuestService hotelGuestService;
    @Autowired private BookingService bookingService;

    @Autowired private ApplicationStateRepository appStateRepository;
    @Autowired private FileDataManipulationService fileDataManipulationService;

    private final PrintStream output = new PrintStream(System.out, true, UTF_8);

    /* ============================= SHOW ========================================== */

    @Command(name = "ПоказатьДанные", abbrev = "sd")
    public void showStateAsJson() {
        output.println(toJsonString(appStateRepository.getCurrentApplicationState()));
    }

    // ========================== Создание ==========================

    @Command(name = "СоздатьГостиницу", abbrev = "ch")
    public void createHotel(@Param(name = "Имя отеля") String name, @Param(name = "Адрес отеля") String address) {
        hotelService.createHotel(name, address);
    }

    @Command(name = "СоздатьНомер", abbrev = "cr")
    public void createHotelRoom(@Param(name = "Тип комнаты") String roomType, @Param(name = "UUID отеля") String hotelUuid) {
        hotelRoomService.createHotelRoom(roomType, hotelUuid);
    }

    @Command(name = "СоздатьГостя", abbrev = "cg")
    public void createHotelGuest(@Param(name = "Имя гостя") String name, @Param(name = "Серия и номер паспорта в формате 'dddd dddddd'") String passportNumber) {
        hotelGuestService.createHotelGuest(name, passportNumber);
    }

    @Command(name = "СоздатьБронирование", abbrev = "cb")
    public void createBooking(@Param(name = "UUID гостя") String guestUuid,
                              @Param(name = "UUID комнаты") String hotelRoomUuid,
                              @Param(name = "Дата заезда в формате 'yyyy-MM-dd'") String checkIn,
                              @Param(name = "Дата выезда в формате 'yyyy-MM-dd'") String checkOut) {
        bookingService.createBooking(guestUuid, hotelRoomUuid, checkIn, checkOut);
    }

    @Command(name = "ОплатитьБронирование", abbrev = "pb")
    public void payBooking(@Param(name = "UUID бронирования") String bookingUuid) {
        bookingService.payBooking(bookingUuid);
    }

    // ========================== Удаление ==========================

    @Command(name = "УдалитьГостиницу", abbrev = "dh")
    public void deleteHotel(@Param(name = "UUID отеля") String uuid) {
        hotelService.deleteHotelById(uuid);
    }

    @Command(name = "УдалитьНомер", abbrev = "dr")
    public void deleteHotelRoom(@Param(name = "UUID комнаты") String hotelRoomUuid) {
        hotelRoomService.deleteHotelRoomById(hotelRoomUuid);
    }

    @Command(name = "ИзвлечьГостя", abbrev = "dg")
    public void pollGuest() {
        HotelGuest guest = hotelGuestService.pollGuest();
        output.println("Извлечён гость:\n" + toJsonString(guest));
    }

    @Command(name = "УдалитьБронирование", abbrev = "db")
    public void deleteBooking(@Param(name = "UUID бронирования") String bookingUuid) {
        bookingService.deleteBookingById(bookingUuid);
    }

    /* ============================= Random ========================================== */

    @Command(name = "СоздатьСлучайныеОтели", abbrev = "crh")
    public void createRandomHotels(@Param(name = "Количество отелей для генерации") int n) {
        times(n, () -> createHotel(faker.animal().name(), faker.address().fullAddress()));
    }

    @Command(name = "СоздатьСлучайныхГостей", abbrev = "crg")
    public void createRandomGuests(@Param(name = "Количество гостей для генерации") int n) {
        times(n, () -> hotelGuestService.createHotelGuest(faker.name().fullName(), faker.regexify("\\d{4} \\d{6}")));
    }

    @Command(name = "СоздатьСлучайнуюКомнатуДляОтеля", abbrev = "crr")
    public void createRandomRoomForHotel(@Param(name = "Количество комнат") int n, @Param(name = "UUID Отеля") String hotelUuid) {
        times(n, () -> hotelRoomService.createHotelRoom(faker.house().room(), hotelUuid));
    }

    /* ============================= Import/Export ========================================== */

    @Command(name = "Импортировать", abbrev = "im")
    public void importState(@Param(name = "Путь до существующего файла") String filePath) {
        fileDataManipulationService.importApplicationStateFrom(filePath);
    }

    @Command(name = "Экспортировать", abbrev = "ex")
    public void exportState(@Param(name = "Путь до ещё не созданного файла") String filePath) {
        fileDataManipulationService.exportCurrentApplicationStateTo(filePath);
    }
}