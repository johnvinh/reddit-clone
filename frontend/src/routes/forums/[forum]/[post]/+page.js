/** @type {import('./$types').PageLoad} */
export async function load({params}) {
    return {
        post: {
            id: params.post
        }
    }
}