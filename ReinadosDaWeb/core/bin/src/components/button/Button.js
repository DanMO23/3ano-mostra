import React from 'react';
import './Button.css';

function Button({ id,children, onClick }) {
    return (
        <button className='buttons' id = {id} onClick={onClick}>{children}</button>
    );
}

export default Button;