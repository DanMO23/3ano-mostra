import React from 'react';
import './BeckButton.css'

function BeckButton({onClick}){
    return(
    <div className='position-back-button'>
        <button className='back-button' onClick= {onClick }></button>
    </div>)
}
export default BeckButton;