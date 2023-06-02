package Macart.Ecommerce.DTO;

import Macart.Ecommerce.Modelos.CardColor;
import Macart.Ecommerce.Modelos.CardType;

import java.time.LocalDate;

public class PagarConTarjetaDTO {

        private CardType type;
        private CardColor color;
        private String number;
        private int cvv;
        private LocalDate thruDate;
        private String email;
        private double amount;

        public PagarConTarjetaDTO() {
        }

        public CardType getType() {return type;}
        public CardColor getColor() {return color;}
        public String getNumber() {return number;}
        public int getCvv() {return cvv;}
        public LocalDate getThruDate() {return thruDate;}
        public String getEmail() {return email;}
        public double getAmount() {return amount;}

}
