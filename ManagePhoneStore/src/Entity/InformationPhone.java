/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Dell
 */
public class InformationPhone {

    String CodePhone;
    String NamePhone;
    double Price;
    int Quantity;
    String Color;
    String OperatingSystem;
    String Brand;
    String CPU;
    int ROM;
    int RAM;
    String SceenResolution;
    String Camera;
    String Battery;
    int YearOfManufacture;
    String Material, Size, Origin, Image;

    public InformationPhone() {
    }

    public InformationPhone(String CodePhone, String NamePhone, double Price, int Quantity, String Color, String OperatingSystem, String Brand, String CPU, int ROM, int RAM, String SceenResolution, String Camera, String Battery, int YearOfManufacture, String Material, String Size, String Origin, String Image) {
        this.CodePhone = CodePhone;
        this.NamePhone = NamePhone;
        this.Price = Price;
        this.Quantity = Quantity;
        this.Color = Color;
        this.OperatingSystem = OperatingSystem;
        this.Brand = Brand;
        this.CPU = CPU;
        this.ROM = ROM;
        this.RAM = RAM;
        this.SceenResolution = SceenResolution;
        this.Camera = Camera;
        this.Battery = Battery;
        this.YearOfManufacture = YearOfManufacture;
        this.Material = Material;
        this.Size = Size;
        this.Origin = Origin;
        this.Image = Image;
    }

    public String getCodePhone() {
        return CodePhone;
    }

    public void setCodePhone(String CodePhone) {
        this.CodePhone = CodePhone;
    }

    public String getNamePhone() {
        return NamePhone;
    }

    public void setNamePhone(String NamePhone) {
        this.NamePhone = NamePhone;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public String getOperatingSystem() {
        return OperatingSystem;
    }

    public void setOperatingSystem(String OperatingSystem) {
        this.OperatingSystem = OperatingSystem;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String Brand) {
        this.Brand = Brand;
    }

    public String getCPU() {
        return CPU;
    }

    public void setCPU(String CPU) {
        this.CPU = CPU;
    }

    public int getROM() {
        return ROM;
    }

    public void setROM(int ROM) {
        this.ROM = ROM;
    }

    public int getRAM() {
        return RAM;
    }

    public void setRAM(int RAM) {
        this.RAM = RAM;
    }

    public String getSceenResolution() {
        return SceenResolution;
    }

    public void setSceenResolution(String SceenResolution) {
        this.SceenResolution = SceenResolution;
    }

    public String getCamera() {
        return Camera;
    }

    public void setCamera(String Camera) {
        this.Camera = Camera;
    }

    public String getBattery() {
        return Battery;
    }

    public void setBattery(String Battery) {
        this.Battery = Battery;
    }

    public int getYearOfManufacture() {
        return YearOfManufacture;
    }

    public void setYearOfManufacture(int YearOfManufacture) {
        this.YearOfManufacture = YearOfManufacture;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String Material) {
        this.Material = Material;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String Size) {
        this.Size = Size;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String Origin) {
        this.Origin = Origin;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    @Override
    public String toString() {
        return this.CodePhone;
    }
}
