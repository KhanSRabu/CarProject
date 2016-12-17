/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DLL;

import java.sql.Date;
import DB.Car;
import DB.CarBookingDetails;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Md. Mazharul Islam
 */
public class CarDLL extends Myconnections 
{
     public CarDLL() {
        super();
    }

    public boolean saveCar(Car aCar) throws SQLException {
        this.setConnection();
        String query = "insert into cardetails(carRegistrationNumber,carEngineNumber,carModel) values(?,?,?)";
        int count = 0;
        try {
            this.statement = this.connection.prepareStatement(query);
            
            this.statement.setString(3, aCar.getregistrationNumber());
            this.statement.setString(2, aCar.getEngineNumber());
            this.statement.setString(1, aCar.getCarModel());
            count = this.statement.executeUpdate();
        } finally {
            this.disconnectDB();
        }
        if (count > 0) {
            return true;
        }
        return false;
    }

    public boolean checkCarAllreadyExists(Car aCar) throws SQLException {
        this.setConnection();
        String query = "select * from cardetails WHERE carRegistrationNumber = ? or carEngineNumber = ?";
        boolean status;
        try {
            this.statement = this.connection.prepareStatement(query);
            this.statement.setString(1, aCar.getregistrationNumber());
            this.statement.setString(2, aCar.getEngineNumber());
            this.resultSet = this.statement.executeQuery();
            if (this.resultSet.isBeforeFirst()) {
                status = true;
            } else {
                status = false;
            }
        } finally {
            this.disconnectDB();
        }
        return status;
    }

    public List<Car> getAllCars() throws SQLException {
        this.setConnection();
        List<Car> myCars = new ArrayList<>();
        String query = "select * from cardetails";
        try {
            this.statement = this.connection.prepareStatement(query);
            this.resultSet = this.statement.executeQuery();
            if (this.resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    Car newCar = new Car(this.resultSet.getString("carModel"), this.resultSet.getString("carEngineNumber"), this.resultSet.getString("carRegistrationNumber"));
                    newCar.setId(this.resultSet.getInt("id"));
                    myCars.add(newCar);
                }
            }
        } finally {
            this.disconnectDB();
        }
        return myCars;
    }

    public List<String> getAllCarsRegNo() throws SQLException {
        this.setConnection();
        List<String> myCars = new ArrayList<>();
        String query = "select carRegistrationNumber from cardetails";
        try {
            this.statement = this.connection.prepareStatement(query);
            this.resultSet = this.statement.executeQuery();
            if (this.resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    String carRegNo = this.resultSet.getString("carRegistrationNumber");
                    myCars.add(carRegNo);
                }
            }
        } finally {
            this.disconnectDB();
        }
        return myCars;
    }

    public ArrayList<String> findCustomer(int customerId) throws SQLException {
        this.setConnection();
        ArrayList<String> customerInfo = new ArrayList<>();

        String query = "select customerName,mobileNumber   from customers where customerId = ? ";

        try {
            this.statement = this.connection.prepareStatement(query);
            this.statement.setInt(1, customerId);
            this.resultSet = this.statement.executeQuery();
            if (this.resultSet.isBeforeFirst())
            {
                while (resultSet.next())
                {
                    customerInfo.add(this.resultSet.getString("customerName"));
                    customerInfo.add(this.resultSet.getString("mobileNumber"));
                }
            }
        } finally {
            this.disconnectDB();
        }
        return customerInfo;

    }

    public boolean saveClient(String name, String mobileNo) throws SQLException {
        this.setConnection();
        String query = "INSERT INTO customers (customerName, mobileNo) VALUES ( ?, ?);";
        int count = 0;
        try {
            this.statement = this.connection.prepareStatement(query);
            this.statement.setString(1, name);
            this.statement.setString(2, mobileNo);
            count = this.statement.executeUpdate();
        } finally {
            this.disconnectDB();
        }
        if (count > 0) {
            return true;
        }
        return false;
    }

    public boolean checkPhoneAllreadyExists(String mobileNo) throws SQLException {
        this.setConnection();
        boolean status;
        String query = "select * from customers where mobileNo = ?";
        try {
            this.statement = this.connection.prepareStatement(query);
            this.statement.setString(1, mobileNo);
            this.resultSet = this.statement.executeQuery();
            status = this.resultSet.isBeforeFirst();
        } finally {
            this.disconnectDB();
        }
        return status;
    }

    public boolean checkBookingExists(String carRegNo, Date fromDate, Date toDate) throws SQLException {
        this.setConnection();
        boolean status = false;
        ArrayList<CarBookingDetails> carBookings = new ArrayList<>();
        String query = "SELECT * FROM `carbookingsTable` WHERE CarRegNo = ? AND RentDate BETWEEN ? AND ?  or ReturnDate BETWEEN ? AND ? ";
        try {
            this.statement = this.connection.prepareStatement(query);
            this.statement.setString(1, carRegNo);
            this.statement.setDate(2, fromDate);
            this.statement.setDate(3, toDate);
            this.statement.setDate(4, fromDate);
            this.statement.setDate(5, toDate);

            this.resultSet = this.statement.executeQuery();
            status = this.resultSet.isBeforeFirst();
        } finally {
            this.disconnectDB();
        }
        return status;
    }

    public boolean bookCar(String carRegNo, int customerId, String customerName, String customerPhoneNo, String location, Date fromDate, Date toDate, double totalCost) throws SQLException {
        this.setConnection();
        String query = "INSERT INTO `carbookingstable` (`CarRegNo`, `CustomerId`, `CustomerName`, `CustomerPhone`, `Location`, `RentDate`, `ReturnDate`, `RentFee`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        int count = 0;

        try {
            this.statement = this.connection.prepareStatement(query);
            this.statement.setString(1, carRegNo);
            this.statement.setInt(2, customerId);
            this.statement.setString(3, customerName);
            this.statement.setString(4, customerPhoneNo);
            this.statement.setString(5, location);
            this.statement.setDate(6, fromDate);
            this.statement.setDate(7, toDate);
            this.statement.setDouble(8, totalCost);
            count = this.statement.executeUpdate();
        } finally {
            this.disconnectDB();
        }
        if (count > 0) {
            return true;
        }
        return false;

    }

    public ArrayList<CarBookingDetails> getDefinedCars(String carRegNo, Date fromDate, Date toDate) throws SQLException {
        this.setConnection();
        String query = "SELECT * FROM `carbookingstable` WHERE CarRegNo = ? and RentDate BETWEEN ? and ?";
        ArrayList<CarBookingDetails> carInfo = new ArrayList<>();
        try {
            this.statement = this.connection.prepareStatement(query);
            this.statement.setString(1, carRegNo);
            this.statement.setDate(2, fromDate);
            this.statement.setDate(3, toDate);
            this.resultSet = this.statement.executeQuery();
            if (this.resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    CarBookingDetails aCarBookingDetail = new CarBookingDetails(this.resultSet.getString("CustomerName"), this.resultSet.getString("CarRegNo"), this.resultSet.getDate("RentDate"), this.resultSet.getDate("ReturnDate"), this.resultSet.getString("Location"), this.resultSet.getDouble("RentFee"));
                    carInfo.add(aCarBookingDetail);
                }
            }
        } finally {
            this.disconnectDB();
        }
        return carInfo;
    }

    public ArrayList<CarBookingDetails> getDefinedInfo(Date fromDate, Date toDate) throws SQLException {
        this.setConnection();
        String query = "SELECT * FROM `carbookingstable` WHERE RentDate BETWEEN ? and ?";
        ArrayList<CarBookingDetails> carIncomeInfo = new ArrayList<>();
        try {
            this.statement = this.connection.prepareStatement(query);
            this.statement.setDate(1, fromDate);
            this.statement.setDate(2, toDate);
            this.resultSet = this.statement.executeQuery();
            if (this.resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    CarBookingDetails aCarBookingDetail = new CarBookingDetails(this.resultSet.getString("CustomerName"), this.resultSet.getString("CarRegNo"), this.resultSet.getDate("RentDate"), this.resultSet.getDate("ReturnDate"), this.resultSet.getString("Location"), this.resultSet.getDouble("RentFee"));
                    carIncomeInfo.add(aCarBookingDetail);
                }
            }
        } finally {
            this.disconnectDB();
        }
        return carIncomeInfo;
        
    }

    public Double getTotalIncome(Date fromDate, Date toDate) throws SQLException {
     this.setConnection();
     String query = "SELECT SUM(RentFee) as total FROM `carbookingsTable` WHERE RentDate BETWEEN ? and ? ";
        double totalIncome = 0;
        try {
            this.statement = this.connection.prepareStatement(query);
            this.statement.setDate(1, fromDate);
            this.statement.setDate(2, toDate);
            this.resultSet = this.statement.executeQuery();
            if (this.resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                   totalIncome = this.resultSet.getDouble("total");
                }
            }
        } finally {
            this.disconnectDB();
        }
        return  totalIncome;
    }

    public boolean checkBookingExists(String carRegNo, java.util.Date fromDate, java.util.Date toDate) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean bookCar(String carRegNo, int customerId, String customerName, String customerPhoneNo, String location, java.util.Date fromDate, java.util.Date toDate, double totalCost) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    public Double getTotalIncome(java.util.Date fromDate, java.util.Date toDate) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<CarBookingDetails> getDefinedCars(String carRegNo, java.util.Date fromDate, java.util.Date toDate) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<CarBookingDetails> getDefinedInfo(java.util.Date fromDate, java.util.Date toDate) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

   

    
      
    
        

       


