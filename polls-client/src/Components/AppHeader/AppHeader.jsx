import { Image, Space, Typography } from 'antd'
import React from 'react'
import pollLogo from '../../polls.png'
import { BarChartOutlined, CaretDownOutlined, HomeOutlined, UserOutlined } from '@ant-design/icons'

function AppHeader() {
  return (
    <div className='AppHeader'>
      <Image width={55} src={pollLogo}/>
      <Typography.Title style={{color: 'rgb(51, 153, 255)'}}>Polling App</Typography.Title>
      <Space>
        <HomeOutlined style={{fontSize: 34}}/>
        <BarChartOutlined style={{fontSize: 34}}/>
        <UserOutlined style={{fontSize: 34}}/>
        <CaretDownOutlined style={{fontSize: 24}}/>
      </Space>
    </div>
  )
}

export default AppHeader
