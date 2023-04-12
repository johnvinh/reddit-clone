<script>
    import {onMount} from "svelte";

    /** @type {import('./$types').PageData} */
    export let data;

    let forumData = {};
    let message = "";
    let posts = [];

    onMount(async () => {
        const response = await fetch(`/api/forum/${data.forum.name}`)
        const text = await response.text();
        if (text === "") {
            message = "There is no forum with that name.";
            return;
        }
        forumData = await response.json();
        posts = forumData.posts;
    });
</script>

{#if message}
    <p>{message}</p>
{/if}
{#if !message}
    <h2>r/{data.forum.name}</h2>

    <ul>
        {#each posts as post}
            <li>{post.title}</li>
        {/each}
    </ul>
{/if}