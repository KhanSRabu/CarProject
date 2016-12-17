/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

/**
 *
 * @author Md. Mazharul Islam
 */
public class Car 
{
    private int id;
    private String carregistrationNumber;
    private String carengineNumber;
    private String carModel;
    

   

    /**
     *
     * @param carModel
     * @param engineNumber
     * @param registrationNumber
     */
    public Car(String carModel, String engineNumber, String registrationNumber) {
        //this.id = id;
        this.carModel = carModel;
        this.carengineNumber = engineNumber;
        this.carregistrationNumber = registrationNumber;
    }

    public int getId() {
        return id;
    }

    public String getEngineNumber() {
        return this.carengineNumber;
    }

    public String getregistrationNumber() {
        return this.carregistrationNumber;
    }

    public String getCarModel() {
        return this.carModel;
    }

    public void setId(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
