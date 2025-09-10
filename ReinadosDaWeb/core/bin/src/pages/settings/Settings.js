import React from 'react';
import {Link} from 'react-router-dom'
import bg from '../../assets/settingsImg/BackgroundConfiguracoes.png'
import BeckButton from '../../components/beck_button/BeckButton'

function Settings(){
    return(
        <body>
            <header>
                <Link to="/">
                    <BeckButton/>
                </Link>
            </header> 
        </body>
    )
}

export default Settings;