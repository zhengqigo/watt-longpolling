package cn.fuelteam.watt.longpolling.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "NOTIFICATION")
public class Notification implements Serializable {

    private static final long serialVersionUID = 3450404715390955851L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TIMESTAMP")
    private Date timestamp;

    @Column(name = "NODE_ID")
    private String nodeId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "PAYLOAD")
    private String payload;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}