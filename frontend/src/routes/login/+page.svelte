<script>
    import { onMount } from "svelte";

    let username = '';
    let password = '';
    let message = '';

    async function handleSubmit(e)
    {
        e.preventDefault();

        const response = await fetch('/api/user/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username,
                password
            })
        });

        const data = await response.json();

        if (response.ok) {
            message = "Login successful!";
            localStorage.setItem("token", data.token);
            localStorage.setItem("username", data.username);
            setTimeout(() => {
                window.location.href = '/';
            }, 1000);
        } else {
            message = data.message;
        }
    }

    onMount(() => {
        // Redirect to home if already logged in
        if (localStorage.getItem("token")) {
            window.location.href = '/';
        }
    });
</script>

<h2>Login</h2>
{#if message}
    <p>{message}</p>
{/if}
<form method="post" on:submit={handleSubmit}>
    <label for="username">Username</label>
    <input id="username" name="username" type="text" bind:value={username} required>

    <label for="password">Password</label>
    <input id="password" name="password" type="password" bind:value={password} required>

    <input name="submit" type="submit">
</form>

<style>
    input[name=submit] {
        display: block;
    }
</style>