<script>
    import {onMount} from "svelte";

    /** @type {import('./$types').PageData} */
    export let data;

    let forumData = {};
    let message = "";
    let posts = [];

    onMount(async () => {
        const response = await fetch(`/api/forum/${data.forum.name}`);
        forumData = await response.text();
        if (forumData === "") {
            message = "There is no forum with that name.";
            return;
        }
        forumData = JSON.parse(forumData);
        posts = forumData["posts"];
        console.log(posts);
    });

    function createPost()
    {
        window.location.href = `/posts/create/${data.forum.name}`;
    }
</script>

{#if message}
    <p>{message}</p>
{/if}
{#if !message}
    <h2>r/{data.forum.name}</h2>

    <input type="button" value="Create Post" on:click={createPost}>
    <ul>
        {#each posts as post}
            <li>{post["title"]}</li>
        {/each}
    </ul>
{/if}