/**
  * Copyright 2019 bejson.com 
  */
package run.download.av.response.episodeinfo;
import java.util.List;

/**
 * Auto-generated: 2019-04-26 19:59:12
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Durl {

    private int order;
    private int length;
    private long size;
    private String ahead;
    private String vhead;
    private String url;
    private List<String> backup_url;
    public void setOrder(int order) {
         this.order = order;
     }
     public int getOrder() {
         return order;
     }

    public void setLength(int length) {
         this.length = length;
     }
     public int getLength() {
         return length;
     }

    public void setSize(long size) {
         this.size = size;
     }
     public long getSize() {
         return size;
     }

    public void setAhead(String ahead) {
         this.ahead = ahead;
     }
     public String getAhead() {
         return ahead;
     }

    public void setVhead(String vhead) {
         this.vhead = vhead;
     }
     public String getVhead() {
         return vhead;
     }

    public void setUrl(String url) {
         this.url = url;
     }
     public String getUrl() {
         return url;
     }

    public void setBackup_url(List<String> backup_url) {
         this.backup_url = backup_url;
     }
     public List<String> getBackup_url() {
         return backup_url;
     }

}