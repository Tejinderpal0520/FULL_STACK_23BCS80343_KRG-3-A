import React from 'react'
import './Form.css'

function Form() {
  return (
    <div className = "w-100 bg-red-500">
        <p className='mt-5'>Name:</p>
      <input className = "mt-1 outline-solid outline rounded-md" type="text" />
      <p className='mt-10'>Email Id:</p>
      <input className = "outline-solid outline rounded-md" type="text"/>
      <p className='mt-10'>Password:</p>
      <input className = "outline-solid outline rounded-md" type="text" />
      <button className = "mt-10 block text-white mt-50px">Log In</button>
    </div>
  )
}

export default Form
