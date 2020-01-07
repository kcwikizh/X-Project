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
public class Improvement {

    private Consume consume;
    private Upgrade upgrade;
    private List<List<Day>> day;
    public void setConsume(Consume consume) {
         this.consume = consume;
     }
     public Consume getConsume() {
         return consume;
     }

    public void setUpgrade(Upgrade upgrade) {
         this.upgrade = upgrade;
     }
     public Upgrade getUpgrade() {
         return upgrade;
     }

    public void setDay(List<List<Day>> day) {
         this.day = day;
     }
     public List<List<Day>> getDay() {
         return day;
     }

}