<script>
    import {onMount} from "svelte";

    /** @type {import('./$types').PageData} */
    export let data;

    let title = "";
    let textualContent = "";
    let link = "";
    let token = "";
    let message = "";
    let forum = data.forum.name;

    onMount(() => {
        token = localStorage.getItem("token");
    });

    async function onSubmit(event) {
        event.preventDefault();
        const response = await fetch("/api/post", {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                title,
                textualContent,
                link,
                forum
            })
        });
        const data = await response.json();
        if (data.message === "Invalid token") {
            localStorage.removeItem("token");
            localStorage.removeItem("username");
            window.location.href = "/login";
        } else if (data.message === "Post created") {
            message = "Your post has been successfully created!";
            window.location.href = `/forums/${data.forum}/${data.id}`;
        } else if (data.message === "Invalid link") {
            message = "The link you provided is invalid.";
        }
    }
</script>

<h2>Create Post</h2>
{#if !token}
    <p>You need to be logged in to create a post</p>
{/if}

{#if message}
    <p>{message}</p>
{/if}

{#if forum}
    <h4>Posting to {forum}</h4>
{/if}
<form on:submit={onSubmit}>
    <label for="title">Title</label>
    <input type="text" name="title" id="title" bind:value={title}>

    <label for="textualContent">Content</label>
    <textarea name="textualContent" id="textualContent" cols="30" rows="10" bind:value={textualContent}></textarea>

    <label for="link">Link</label>
    <input type="text" name="link" id="link" bind:value={link}>

    <input type="submit" value="Submit">
</form>