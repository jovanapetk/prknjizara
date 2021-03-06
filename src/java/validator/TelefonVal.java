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
@FacesValidator("validator.TelefonVal")
public class TelefonVal implements Validator{

   @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
       String broj=String.valueOf(value);
        if (broj.length()!=11){
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška","Vrednost mora imati tačno 11 karaktera"));
        }
        
            
        
                if(broj.charAt(3)!='/'){
           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greska", "Format unosa nije 000/0000000"));
        }
    }
    
    
}
