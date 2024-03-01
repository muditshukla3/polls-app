import { Button, Form, Input, Result, message } from "antd";
import { Link } from "react-router-dom";
import axios from '../../api/axios';

const USERNAME_REGEX = /^[a-zA-Z][a-zA-Z0-9-_]{2,10}$/;
const PASSWORD_REGEX = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d!@#$]{6,15}$/;
function Register() {
  
  const onFinish = async (values) => {
    console.log('Received values:', values);
    
    try{
        const response = await axios.post("/auth/register", 
                                      {
                                        name : values.fullName,
                                        email: values.email,
                                        password: values.password,
                                        username: values.username 
                                      })
        if(response.data.success){
          return <Result status="success"
          title="User Registered Successfully"
          extra={[
              <p>
                <Link to="/login">Login</Link>
              </p>,
          ]} />; 
        }

        message.error('Registration Failed')
        return;
      }catch(err){
        console.error('Error: ', err)
        message.error('Registration Failed')
    }
  };

  const validateUsername = async (username) => {
    try {
      const response = await axios.get(`/users/validate/${username}`);
      if (!response.data.available) {
        return 'false';
      }
      return 'true';
    } catch (error) {
      return 'error';
    }
  };

  const validateEmail = async (email) => {
    try {
      const response = await axios.get(`/users/email/validate/${email}`);
      if (!response.data.available) {
        return 'false';
      }
      return 'true';
    } catch (error) {
      return 'error';
    }
  };
  
  return (
        <div style={{width: '400px', height:'400px'}}>
            <p>Register</p>
            <Form 
                  autoComplete="off" 
                  labelCol={{span:10}} 
                  wrapperCol={{span: 14}}
                  layout="horizontal"
                  size="large"
                  onFinish={onFinish}
                  >
              <Form.Item 
                name="fullName" 
                label="Full Name"
                rules={[
                  {
                    required: true,
                    message: "Please enter your full name"
                  },
                  {
                    whitespace: true,
                    message: "Whitespaces are not allowed"
                  }
                ]}
                >
                <Input placeholder="Type your full name" />
              </Form.Item>
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
                  },
                  {
                    pattern: USERNAME_REGEX,
                    message: 'Username must start with a letter and be 3 to 8 characters long, containing only letters, digits, underscores, or hyphens.'
                  },
                  () => ({
                    validator(_, value){
                      if(value.length > 3){
                        const result = validateUsername(value)
                        if(result === 'true'){
                            return Promise.resolve()
                        }else if(result === 'false'){
                            return Promise.reject('Username already registered')
                        }else{
                          return Promise.reject("Error validating username")
                        }
                      }
                    }
                  })
                ]}
                >
                <Input placeholder="Type your username" />
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
                  },
                  {
                    pattern: PASSWORD_REGEX,
                    message: 'Password must be 6 to 15 characters long, must contain letters and digits, special chars allowed !@#$ .'
                  }
                ]}
                >
                <Input.Password placeholder="Type your password"/>
              </Form.Item>
              <Form.Item 
                name="confirmPassword" 
                label="Confirm Password"
                rules={[
                  {
                    required: true,
                  },
                  ({getFieldValue}) => ({
                    validator(_, value){
                      if(!value || getFieldValue('password') === value){
                        return Promise.resolve()
                      }
                      return Promise.reject("The two passwords does not match")
                    }
                  })
                ]}
                >
                <Input.Password placeholder="Confirm password"/>
              </Form.Item>
              <Form.Item 
                 name="email" 
                 label="Email"
                 rules={[
                  {
                    required: true,
                  },
                  {
                    type: "email"
                  },
                  () => ({
                    validator(_, value){
                      if(value.length > 6){
                        const result = validateEmail(value)
                        if(result === 'true'){
                            return Promise.resolve()
                        }else if(result === 'false'){
                            return Promise.reject('Email already registered')
                        }else{
                          return Promise.reject("Error validating email")
                        }
                      }
                    }
                  })
                ]}>
                <Input placeholder="Type your email" />
              </Form.Item>
              <Form.Item wrapperCol={{span: 24}}>
                <Button block type="primary" htmlType="submit">Register</Button>
              </Form.Item>
              <p>
                Already Registered? <Link to="/login">Login</Link>
              </p>
            </Form>
          
          </div>
        
    
  );
}

export default Register;
