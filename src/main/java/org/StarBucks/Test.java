package org.StarBucks;

import org.StarBucks.Coffee.Beverage;
import org.StarBucks.Coffee.Espresso;
import org.StarBucks.Coffee.HouseBlend;
import org.StarBucks.Condiment.Caramel;
import org.StarBucks.Condiment.Mocha;
import org.StarBucks.Size.Large;
import org.StarBucks.Size.Medium;
import org.StarBucks.Size.Super;

import java.util.Scanner;

/**
 * @author liubi
 * @date 2020-08-27 18:14
 **/
public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("welcome to starbucks,which coffee do you prefer?");
            System.out.println("1. Espresso             2.HouseBlend");
            Beverage beverage;
            switch (sc.nextInt()) {
                case 1:
                    beverage = new Espresso();
                    break;
                case 2:
                    beverage = new HouseBlend();
                    break;
                default:
                    throw new IllegalArgumentException("wrong input");
            }


            System.out.println("which size do you prefer?");
            System.out.println("1. Medium             2.Large             3.Super Large");
            switch (sc.nextInt()) {
                case 1:
                    beverage = new Medium(beverage);
                    break;
                case 2:
                    beverage = new Large(beverage);
                    break;
                case 3:
                    beverage = new Super(beverage);
                    break;
                default:
                    throw new IllegalArgumentException("wrong input");
            }


            System.out.println("Do you want some Condiments?");
            System.out.println("1. Caramel             2.Mocha             3.No,thx");
            switch (sc.nextInt()) {
                case 1:
                    beverage = new Caramel(beverage);
                    break;
                case 2:
                    beverage = new Mocha(beverage);
                    break;
                default:
            }

            double price = beverage.calPrice();
            System.out.println("Coffee you ordered:" + beverage.getDescription());
            System.out.println("CNY:" + price + " ,Thx!");
        }
    }
}
