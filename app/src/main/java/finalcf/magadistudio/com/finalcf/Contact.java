package finalcf.magadistudio.com.finalcf;

/**
 * Created by hrido on 12/17/2017.
 */

public class Contact {

    int _id;
    String _name;
    String _imageurl;


    public Contact(){

    }

    public Contact(int  id, String name , String _imageurl){
        this._id = id;
        this._name = name;
        this._imageurl=_imageurl;

    }
    public Contact( String name , String _imageurl){

        this._name = name;
        this._imageurl = _imageurl;

    }
    public Contact( String name){

        this._name = name;


    }

    public int getID(){
        return this._id;
    }
    public  String get_imageurl() { return this._imageurl ; }

    public void setID(int id){
        this._id = id;
    }

    public String getName(){
        return this._name;
    }

    public void setName(String name){
        this._name = name;
    }
    public void set_imageurl(String _imageurl){this._imageurl = _imageurl ; }


}