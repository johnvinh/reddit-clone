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
    {#each posts as post}
        <div class="post-box">
            {#if post["link"]}
                <a class="post-title" href='{post["link"]}'>{post["title"]}</a>
            {/if}
            {#if !post["link"]}
                <a class="post-title" href='/forums/{post["forum"]["name"]}/{post["id"]}'>{post["title"]}</a>
            {/if}
            <div>
                <a href='/forums/{post["forum"]["name"]}/{post["id"]}'>View Comments</a>
            </div>
        </div>
    {/each}
{/if}

<style>
    .post-title {
        font-size: 20px;
        font-weight: bold;
    }

    .post-box {
        border: 1px solid black;
        margin: 10px;
        padding: 10px;
    }
</style>