package K23CNT1.natProject3.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "nat_booking_equipments") // 1. Tên bảng trong DB
@Data
public class BookingEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nat_id") // 2. Khóa chính
    private Long id;

    @Column(name = "nat_quantity") // 3. Số lượng
    private Integer quantity;

    // Quan hệ N-1 với Booking
    @ManyToOne
    @JoinColumn(name = "nat_booking_id") // 4. Khóa ngoại trỏ về nat_bookings
    private Booking booking;

    // Quan hệ N-1 với Equipment
    @ManyToOne
    @JoinColumn(name = "nat_equipment_id") // 5. Khóa ngoại trỏ về nat_equipments
    private Equipment equipment;
}