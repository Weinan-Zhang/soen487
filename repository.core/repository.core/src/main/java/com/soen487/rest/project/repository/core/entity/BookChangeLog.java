package com.soen487.rest.project.repository.core.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.soen487.rest.project.repository.core.configuration.LogType;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="tab_book_change_log")
public class BookChangeLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bcl_lid")
    private long lid;
    @Column(name="bcl_log_time", nullable = false)
    private Timestamp logTime;
    @Column(name="bcl_log_type", nullable = false)
    private LogType logType;
    @ManyToOne
    @JoinColumn(name="bcl_b_bid", foreignKey=@ForeignKey(name="none", value=ConstraintMode.NO_CONSTRAINT))
    private com.soen487.rest.project.repository.core.entity.Book book;

    public BookChangeLog(){}

    public BookChangeLog(Timestamp logTime, LogType logType) {
        this.logTime = logTime;
        this.logType = logType;
    }

    public long getLid() {
        return lid;
    }

    public void setLid(long lid) {
        this.lid = lid;
    }

    public Timestamp getLogTime() {
        return logTime;
    }

    public void setLogTime(Timestamp logTime) {
        this.logTime = logTime;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public com.soen487.rest.project.repository.core.entity.Book getBook() {
        return book;
    }

    public void setBook(com.soen487.rest.project.repository.core.entity.Book book) {
        this.book = book;
    }
}
