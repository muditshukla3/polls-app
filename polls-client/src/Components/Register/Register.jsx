import { Button, Form, Input } from "antd";
import React from "react"; 

function Register() {
  return (
        <div style={{width: '400px', height:'400px'}}>
            <p>Register</p>
            <Form labelCol={{span:10}} wrapperCol={{span: 14}}>
              <Form.Item name="fullName" label="Full Name">
                <Input placeholder="Type your full name" />
              </Form.Item>
              <Form.Item name="username" label="Username">
                <Input placeholder="Type your username" />
              </Form.Item>
              <Form.Item name="password" label="Password">
                <Input.Password placeholder="Type your password"/>
              </Form.Item>
              <Form.Item name="email" label="Email">
                <Input placeholder="Type your email" />
              </Form.Item>
              <Form.Item wrapperCol={{span: 24}}>
                <Button block type="primary" htmlType="submit">Register</Button>
              </Form.Item>
            </Form>
          
          </div>
        
    
  );
}

export default Register;
