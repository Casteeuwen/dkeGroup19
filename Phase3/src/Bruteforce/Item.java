package Bruteforce;

public class Item {
    protected String name = "";
    protected int value = 0;
    protected double weight = 0;
    protected double volume = 0;

    public Item() {
    }

    public Item(String name, int value, double weight, double volume){ //take in name, value, weight and volume of an item.
        setName(name);
        setValue(value);
        setWeight(weight);
        setVolume(volume);
    }

    public int getValue(){ //return value of the item
        return value;
    }

    public void setValue(int value){ //set value of the item
        this.value = Math.max(value, 0);
    }

    public double getWeight(){ //return weigth of the item
        return weight;
    }

    public void setWeight(double weight){ //set weight of the item
        this.weight = Math.max(weight, 0);
    }

    public double getVolume(){ //return volume of the item
        return volume;
    }

    public void setVolume(double volume){ //set volume of the item
        this.volume = Math.max(volume, 0);
    }

    public String getName(){ //return name of the item
        return name;
    }

    public void setName(String name){ //set the name of the item
        this.name = name;
    }
}
