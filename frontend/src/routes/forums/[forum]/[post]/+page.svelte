<script>
    import {onMount} from "svelte";

    /** @type {import('./$types').PageData} */
    export let data;

    let message;
    let title = "";
    let textualContent = "";
    let token = "";
    let postId;
    let commentContent = "";
    let comments = "";

    onMount(async () => {
        token = localStorage.getItem("token");

        const response = await fetch(`/api/post/${data.post.id}`,
            {
                method: "GET",
                headers: {
                    "Accept": "application/json"
                }
            });

        const text = await response.text();

        if (text === "") {
            message = "That post doesn't exist.";
            return;
        }
        const json = JSON.parse(text);
        console.log(json);
        title = json["title"];
        textualContent = json["textualContent"];
        console.log(textualContent);
        postId = json["id"];
        comments = json["comments"];
    });

    async function onSubmit(event) {
        event.preventDefault();
        const response = await fetch("/api/comment/create", {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                content: commentContent,
                postId
            })
        });
    }
</script>

<h2>{title}</h2>
{#if message}
    <p>{message}</p>
{/if}
{#if !message}
    <p>{textualContent}</p>
{/if}

<form on:submit={onSubmit}>
    <label for="comment">Comment</label>
    <textarea id="comment" name="comment" rows="4" cols="50" bind:value={commentContent}></textarea>
    <button type="submit">Submit</button>
</form>

{#if comments}
    {#each comments as comment}
        <div>
            <p>{comment.body}</p>
            <p>{comment.author.username}</p>
        </div>
    {/each}
{/if}
