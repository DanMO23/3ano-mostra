import React from 'react';
import './IconWithLabel.css'

function IconWithLabel({ icon, label }) {
    return (
        <div className="icon-with-label">
            <span className="icon">{icon}</span>
            <span className="label">{label}</span>
        </div>
    );
}

export default IconWithLabel;
