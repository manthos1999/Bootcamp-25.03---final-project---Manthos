import Head from "next/head";
import {useEffect, useState} from "react";
import axios from "axios";


/**
 * 1. User fill in email and password to login
 * 2. send POST /login with Basic Auth to the server
 * 3. if success,
 *     - Store token in local storage
 *     - redirect to chat page
 * @return {JSX.Element}
 * @constructor
 */
export default function LoginPage() {

    let surname = "Sekas"

    const [email, setEmail] = useState(null);
    const [password, setPassword] = useState(null);
    const [loading, setLoading] = useState(false)


    useEffect(() => {


        let token = localStorage.getItem("token");

        if (token != null) {
            window.location.href = "/chat";
        }
    }, []);


    function handleEmailChange(event) {
        console.log("Email changed to: ", event.target.value);
        setEmail(event.target.value);
    }

    function handlePasswordChange(event) {
        setPassword(event.target.value);
    }

    async function handleLogin() {
        setLoading(true);

        let credentials = btoa(email + ":" + password);

        try {

            let response = await axios.post(
                "http://localhost:8080/users/login",
                {},                            // no body
                {
                    headers: {
                        "Authorization": `Basic ${credentials}`,
                        "Content-Type": "application/json"
                    }
                }
            )

            console.log("Login successful");
            // Store token in local storage
            localStorage.setItem("token", response.data.token);

            setLoading(false);
            // Redirect to chat page
            window.location.href = "/chat";

        } catch (e) {
            console.log("Login failed");
            alert("Login failed. Please check your email and password.");
            setEmail(null)
            setPassword(null)
            setLoading(false);
        }


        console.log("Response: ", response.data);


    }




    return (
        <>
            <div className="page-container">
                <header>
                    <div className="header-content">
                        <div className="header-brand">
                            <img src="./bootcamp-2025.03-logo.jpg" alt="Logo" className="header-logo"/>
                            <div className="header-title">Chat Application</div>
                        </div>
                        <div className="profile-dropdown">
                            <input type="checkbox" id="profile-toggle"/>
                            <label htmlFor="profile-toggle" className="profile-icon">JD</label>
                            <div className="dropdown-menu">
                                <a href="#">Profile</a>
                                <a href="#">Settings</a>
                                <a href="#">Logout</a>
                            </div>
                            <label htmlFor="profile-toggle" className="overlay"></label>
                        </div>
                    </div>
                </header>

                <div className="content">
                    <div className="tab-container">
                        <input type="radio" id="tab-login" name="tab" checked/>
                        <input type="radio" id="tab-signup" name="tab"/>
                        <div className="tabs">
                            <label htmlFor="tab-login">Login</label>
                            <label htmlFor="tab-signup">Sign Up</label>
                        </div>

                        <section className="form-section login">
                            <form onSubmit={handleLogin}>
                                <div className="form-group">
                                    <label htmlFor="login-email">Email</label>
                                    <input type="email" id="login-email" placeholder="you@example.com"
                                           value={email}
                                           onChange={handleEmailChange}
                                    />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="login-password">Password</label>
                                    <input type="password" id="login-password" placeholder="••••••••"
                                           value={password}
                                           onChange={handlePasswordChange}
                                    />
                                </div>
                                {/* condition ? first : second */}
                                {loading ? (
                                    <div className="form-actions">
                                        Loading…
                                    </div>
                                ) : (
                                    <div className="form-actions">
                                        <button type="submit" className="btn btn-primary">
                                            Sign In
                                        </button>

                                        <label htmlFor="tab-signup" className="btn btn-link">
                                            Create Account
                                        </label>
                                    </div>
                                )}
                            </form>
                        </section>

                        <section className="form-section signup">
                            <form>
                                <div className="form-group">
                                    <label htmlFor="signup-name">Name</label>
                                    <input type="text" id="signup-name" placeholder="John Doe"/>
                                </div>
                                <div className="form-group">
                                    <label htmlFor="signup-email">Email</label>
                                    <input type="email" id="signup-email" placeholder="you@example.com"/>
                                </div>
                                <div className="form-group">
                                    <label htmlFor="signup-password">Password</label>
                                    <input type="password" id="signup-password" placeholder="••••••••"/>
                                </div>
                                <div className="form-group">
                                    <label htmlFor="signup-confirm">Confirm Password</label>
                                    <input type="password" id="signup-confirm" placeholder="••••••••"/>
                                </div>
                                <div className="form-actions">
                                    <button type="submit" className="btn btn-primary">Create Account</button>
                                    <label htmlFor="tab-login" className="btn btn-link">Sign In</label>
                                </div>
                            </form>
                        </section>
                    </div>
                </div>

                <footer>© 2025 Chat App, Inc.</footer>
            </div>
        </>
    );
}
