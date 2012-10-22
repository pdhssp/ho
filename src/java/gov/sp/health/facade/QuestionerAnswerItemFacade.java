/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.sp.health.facade;

import gov.sp.health.entity.QuestionerAnswerItem;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author IT
 */
@Stateless
public class QuestionerAnswerItemFacade extends AbstractFacade<QuestionerAnswerItem> {
    @PersistenceContext(unitName = "HOPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuestionerAnswerItemFacade() {
        super(QuestionerAnswerItem.class);
    }
    
    
    
    
    
    
}
