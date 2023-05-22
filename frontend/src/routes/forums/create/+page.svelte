<script>
    import {onMount} from "svelte";

    let token = "";
    let name = "";
    let description = "";

    let message = "";

    onMount(() => {
        token = localStorage.getItem("token");
    });

    async function onSubmit(e) {
        e.preventDefault();

        const response = await fetch("/api/forum/create",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify({name, description})
            });

        const data = await response.json();
        if (data.message === "Taken") {
            message = "That name is taken. Please choose another one.";
        } else if (data.message === "Forum created") {
            message = "Forum successfully created!";
            window.location.href = `/forums/${name}`;
        } else if (data.message === "Invalid token") {
            localStorage.removeItem("token");
            localStorage.removeItem("username");
            window.location.href = "/login";
        }
    }
</script>

<h2>Create New Forum</h2>

{#if message}
    <p>{message}</p>
{/if}
<form on:submit={onSubmit}>
    <label for="forumName">Name</label>
    <input type="text" id="forumName" bind:value={name}>

    <label for="description">Description</label>
    <textarea id="description" bind:value={description}></textarea>

    <input type="submit" value="Create Forum">
</form>