package com.damily.pkds.entity;

import java.util.List;

/**
 * Created by Dandan.Cao on 2016/12/9.
 */
public class DeviceInfo  {
    private boolean status;
    private MessageBean message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    public static class MessageBean {
       private List<RecordsBean> records;

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        public static class RecordsBean {
            private String deviceRegId;
            private String typeName;
            private String submitTime;
            private String submitUser;
            private Object maintaiUser;
            private String directoryIP;

            public String getDeviceRegId() {
                return deviceRegId;
            }

            public void setDeviceRegId(String deviceRegId) {
                this.deviceRegId = deviceRegId;
            }

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public String getSubmitTime() {
                return submitTime;
            }

            public void setSubmitTime(String submitTime) {
                this.submitTime = submitTime;
            }

            public String getSubmitUser() {
                return submitUser;
            }

            public void setSubmitUser(String submitUser) {
                this.submitUser = submitUser;
            }

            public Object getMaintaiUser() {
                return maintaiUser;
            }

            public void setMaintaiUser(Object maintaiUser) {
                this.maintaiUser = maintaiUser;
            }

            public String getDirectoryIP() {
                return directoryIP;
            }

            public void setDirectoryIP(String directoryIP) {
                this.directoryIP = directoryIP;
            }
        }
    }
}
