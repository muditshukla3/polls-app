import { LockOutlined, UserOutlined } from "@ant-design/icons";
import { Button, Form, Input } from "antd";
import React from 'react'
import { Link } from "react-router-dom";

function Login() {
  return (
    <div style={{width: '400px', height:'400px'}}>
            <p>Login</p>
            <Form labelCol={{span:10}} wrapperCol={{span: 14}}>
              
              <Form.Item 
                    name="username" 
                    label="Username"
                    rules={[
                      {
                        required: true,
                      },
                      {
                        whitespace: true,
                        message: "Whitespaces are not allowed"
                      }
                    ]}>
                <Input 
                    prefix = {<UserOutlined className="site-form-item-icon"/>}
                    placeholder="Type your username" />
              </Form.Item>
              <Form.Item 
                  name="password" 
                  label="Password"
                  rules={[
                    {
                      required: true,
                    },
                    {
                      whitespace: true,
                      message: "Whitespaces are not allowed"
                    }
                  ]}
                  >
                <Input.Password 
                    prefix={<LockOutlined className="site-form-item-icon" />}
                    placeholder="Type your password"/>
              </Form.Item>
              <Form.Item wrapperCol={{span: 14}}>
                Don't have account? <Link to="/register">Signup</Link>
              </Form.Item>
              <Form.Item wrapperCol={{span: 24}}>
                <Button block type="primary" htmlType="submit">Login</Button>
              </Form.Item>
            </Form>
          
          </div>
        
  )
}

export default Login
