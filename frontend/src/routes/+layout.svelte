<script>
    import "$lib/css/normalize.css";
    import "$lib/css/sakura.css";
    import {onMount} from "svelte";

    let username = "";

    onMount(() => {
        if (localStorage.getItem("username")) {
            username = localStorage.getItem("username");
        }
    });

    function logOut()
    {
        localStorage.removeItem("username");
        username = "";
        localStorage.removeItem("token");
    }
</script>

<header>
    {#if username}
        <p>{username}</p>
    {/if}
    <nav>
        {#if username}
            <a href="#" on:click={logOut}>Logout</a>
        {/if}
        {#if !username}
            <a href="/login">Login</a>
            <a href="/register">Register</a>
        {/if}
    </nav>
</header>
<main>
    <slot></slot>
</main>