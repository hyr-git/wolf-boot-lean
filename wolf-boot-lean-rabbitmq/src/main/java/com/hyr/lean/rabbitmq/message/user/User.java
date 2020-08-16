package com.hyr.lean.rabbitmq.message.user;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class User implements Serializable{
        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private String name;
        private String pass;
        private Date birthday;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getPass() {
            return pass;
        }
        public void setPass(String pass) {
            this.pass = pass;
        }
}