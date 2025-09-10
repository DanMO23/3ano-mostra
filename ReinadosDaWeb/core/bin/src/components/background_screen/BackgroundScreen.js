import React from 'react';
import './BackgroundScreen.css'; 

function BackgroundScreen({ title, children }) {
    return (
        <div className="background-screen">
            <div className="title">{title}</div>
            <div className='dots'>
                <div className='dot'></div>
                <div className='dot'></div>
                <div className='dot'></div>
            </div>
            {children}
        </div>
    );
}

export default BackgroundScreen;
