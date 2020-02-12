package cn.yangcy.pzc.model.imformation;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tb_information")
public class Information implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "createOn")
    private String createOn;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "isDelete",defaultValue = "0")
    private int isDelete;

    @ColumnInfo(name = "hits",defaultValue = "0")
    private int hits;

    public Information(String type, String title, String author, String createOn, String content) {
        this.type = type;
        this.title = title;
        this.author = author;
        this.createOn = createOn;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateOn() {
        return createOn;
    }

    public void setCreateOn(String createOn) {
        this.createOn = createOn;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    @Override
    public String toString() {
        return "Information{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", createOn='" + createOn + '\'' +
                ", content='" + content + '\'' +
                ", isDelete=" + isDelete +
                ", hits=" + hits +
                '}';
    }
}
