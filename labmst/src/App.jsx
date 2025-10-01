import { useState } from 'react'
import './App.css'

function App() {
  const [count, setCount] = useState(0)
  

  return (
    <>
      
      <div className="que1">
        <h2>This is the question 1</h2>
        <h4>The current value of counter is: {count}</h4>
        <button className ="buttons" onClick={() => {
          if(count < 9)setCount((count) => count + 1);
          else alert("Maximum limit reached");
          }}>
          Increment
        </button>
        <button className ="buttons" onClick={() => {
          if(count > 0)setCount((count) => count - 1);
          else alert("Minimum limit reached");
          }}>
          Decrement
        </button>
        <br />
        <button className ="buttons" onClick={() => {setCount(count => 0) 
          alert("Counter reseted");
        }
        } >Reset</button>

      </div>
      <div className='que2'>
        <h2>This is the question 2</h2>
        <form action="showdata()">
        <p>Name</p>
        <input type="text" name='Name'/>
        <p>Email</p>
        <input type="email" name = 'Email'/>
        <p>Course</p>
        <input type="text" name = 'Course'/>
        <br />
        <button type = "submit">Submit</button>
        </form>
      </div>
    </>
  )
}

export default App
