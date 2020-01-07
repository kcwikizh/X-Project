/**
  * Copyright 2020 bejson.com 
  */
package kcwiki.akashi.spider.entity.slotitem.kcdata;
import java.util.List;

/**
 * Auto-generated: 2020-01-07 16:54:21
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Consume {

    private List<Useitem> useitem;
    private Slotitem slotitem;
    private List<Integer> resource;
    public void setUseitem(List<Useitem> useitem) {
         this.useitem = useitem;
     }
     public List<Useitem> getUseitem() {
         return useitem;
     }

    public void setSlotitem(Slotitem slotitem) {
         this.slotitem = slotitem;
     }
     public Slotitem getSlotitem() {
         return slotitem;
     }

    /**
     * @return the resource
     */
    public List<Integer> getResource() {
        return resource;
    }

    /**
     * @param resource the resource to set
     */
    public void setResource(List<Integer> resource) {
        this.resource = resource;
    }

}