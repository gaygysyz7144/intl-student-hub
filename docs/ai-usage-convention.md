# AI Usage Convention

## Allowed
- Research and learning (explaining concepts, comparing options)
- Scaffolding and boilerplate (entities, mappers, DTOs, basic components)
- Unit test drafts
- Copywriting for UI text and documents (then edited by me)
- First-pass code review (summary, possible bugs)
- Debugging help from error logs

## Not allowed
- Having AI write an entire module without review
- Submitting code I do not understand
- Letting AI make key decisions (architecture, security design)
- Uploading real user data, passwords, keys or other sensitive information to a public model

## Rules
1. **Labeling:** every AI-generated or AI-modified block carries `// AI-assisted` (Java) or `<!-- AI-assisted -->` (Vue/HTML).
2. **Human review:** I read and run all AI output before it enters a PR. I must be able to explain every line.
3. **Key decisions** (architecture, authentication, data model, security) are made by me; AI may only give options.
4. **Logging:** each meaningful use is recorded in `docs/04-ai-usage-log.md` (tool, task, key prompt idea, how verified).
5. **Data:** test data is fake. Real emails, phone numbers and passwords are never sent to an AI tool.
6. **Copyright:** I check licenses of AI-suggested code and libraries and follow open-source terms.
7. **Honesty:** the product and documents state what the AI features can and cannot do.

Signed: [Your name]    Date: [date]
