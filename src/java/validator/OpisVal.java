/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validator;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Korisnik
 */
@FacesValidator("validator.OpisVal")
public class OpisVal implements Validator{
   @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
       String opis=String.valueOf(value);
        if (opis.length()>10000){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška","Opis radnje ne sme imati više od 10000 karaktera"));
        }
       
    }
     
}
